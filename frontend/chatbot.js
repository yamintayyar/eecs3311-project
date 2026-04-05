const CHAT_BASE_URL = "http://localhost:8080";

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
    
    const typingBubble = createMessageDiv("...", "received")
    chatHistory.appendChild(typingBubble);
    
    chatHistory.scrollTop = chatHistory.scrollHeight;


    try {
        const res = await fetch(`${CHAT_BASE_URL}/api/chat`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({message: userMessage})
        });

        
        if (typingBubble) {
            typingBubble.remove();
        }

        if (!res.ok) {
            alert("Connection to Gemini failed!");
            return;
        }

        const data = await res.json();
        console.log(data);
        botMessage = data.reply;

    } catch (err) {

        if (typingBubble) {
            typingBubble.remove();
        }
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