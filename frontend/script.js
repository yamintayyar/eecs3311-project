const BASE_URL = "http://localhost:8080";

function checkAutoLogin() {
    const role = localStorage.getItem("role");

    if (role) {
        redirectToDashboard(role);
    }
}

window.addEventListener("DOMContentLoaded", () => {
    // Only run on index page
    if (window.location.pathname.includes("index.html") || window.location.pathname === "/") {
        checkAutoLogin();
    }
});

function getRoleEndpoint(role) {
    switch (role) {
        case "client":
            return "clients";
        case "consultant":
            return "consultants";
        case "admin":
            return "admins";
        default:
            return "clients";
    }
}

function redirectToDashboard(role) {
    if (role === "client") {
        window.location.href = "client.html";
    } else if (role === "consultant") {
        window.location.href = "consultant.html";
    } else if (role === "admin") {
        window.location.href = "admin.html";
    }
}

// REGISTER
async function register() {
    const role = document.getElementById("role").value;
    const endpoint = getRoleEndpoint(role);

    const data = {
        name: document.getElementById("regName").value,
        email: document.getElementById("regEmail").value,
        password: document.getElementById("regPassword").value
    };

    try {
        console.log(`${BASE_URL}/${endpoint}`);
        const res = await fetch(`${BASE_URL}/${endpoint}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        });

        const result = await res.json();
        document.getElementById("response").innerText =
            "Registered: " + JSON.stringify(result);

    } catch (err) {
        document.getElementById("response").innerText =
            "Error: " + err.message;
    }
}

// LOGIN 
async function login(event) {
    event.preventDefault();
    const role = document.getElementById("role").value;
    const endpoint = getRoleEndpoint(role);

    const data = {
        email: document.getElementById("loginEmail").value,
        password: document.getElementById("loginPassword").value
    };

    try {
        const res = await fetch(`${BASE_URL}/${endpoint}/login`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        });

        if (!res.ok) {
        alert("Login failed");
        return;
        }

        const user = await res.json();
        console.log(user);

        //save user
        localStorage.setItem("user", JSON.stringify(user));
        localStorage.setItem("role", role);

        //user's dashboard
        redirectToDashboard(role);

    } catch (err) {
        console.log(err);
    }
}











//CHATBOT
let buttonState = false;

const chatToggleBtn = document.getElementById("chat-btn");

const chatIn = document.getElementById("userInput");
const sendChatInBtn = document.getElementById("sendBtn");

const chatHistory = document.querySelector(".chat-box-body");


function createMessageDiv(message, className){
    const newDiv = document.createElement("div");
    newDiv.classList.add("message", className);
    let p = document.createElement("p");
    p.textContent = message;
    newDiv.appendChild(p);
    return newDiv;
}



async function sendMessage(){
    const userMessage = chatIn.value;
    chatHistory.appendChild(createMessageDiv(userMessage, "sent"));
    chatIn.value = "";
    let botMessage = "Something went wrong!";

    try {
        const res = await fetch(`${BASE_URL}/api/chat`, {
            method: "POST",
            mode: "no-cors",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({message: userMessage})
        });

        if (!res.ok) {
        alert("Connection to Gemini failed!");
        return;
        }

        const data = await res.json();
        console.log(data);

        botMessage = data.reply;

    } catch (err) {
        console.log(err);
    }

    chatHistory.appendChild(createMessageDiv(botMessage, "received"));

}


sendChatInBtn.addEventListener("click", sendMessage);


    //Toggle Button 
chatToggleBtn.onclick = () => {
    buttonState = !buttonState;
    console.log(buttonState);
    UpdateButton();
}

function UpdateButton(){
    if (buttonState == true) {
        chatToggleBtn.textContent= "x";
        document.body.classList.toggle("show-chatbot");
    } 
    else {
        chatToggleBtn.textContent = "💬";
        document.body.classList.toggle("show-chatbot");
    }
}