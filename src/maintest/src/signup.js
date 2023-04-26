import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const SignUp = () =>{
    const navigate = useNavigate();

    const [usernameinput, setUsernameinput] = useState("");
    const [emailinput, setEmailinput] = useState("");
    const [passwordinput, setPassowrdinput] = useState("");
    const [message, setMessage] = useState("");

    const registeraxios = () =>{
        axios
        .post("/server/server.js", {
            username: usernameinput,
            email: emailinput,
            password: passwordinput,
        })
        .then((response) => {
            console.log(response);
            alert("회원가입성공");
            if ((response.status = 200)) {
                return navigate("/App.js");
            }
        }).catch((err) =>{
            setMessage(err.response.data.message)
            console.log(err);
        });
    };
    return(
        <div className="signup">
            <div className="signup_input">
            <label>name</label>
            <input
            type="text"
            placeholder="이름"
            onChange={(e)=> {
                setUsernameinput(e.target.value);
            }} 
        />
        </div>
        <div className="signup_input">
            <label>Email</label>
            <br/>
            <input
            type="text"
            placeholder="이메일"
            onChange={(e)=>{
                setEmailinput(e.target.value);
            }}
            className={!message? "inputLogin": "err_passowrd" }
            />
        </div>
        <div className="signup_input">
            <label>password</label>
            <input 
            type="password"
            placeholder="비밀번호"
            onChange={(e)=>{
                setPassowrdinput(e.target.value);
            }}
            className={!message? "inputLogin" : "err_password"}
            />
            </div>
            <button onClick={registeraxios}>회원가입</button>
        </div>
    )
};