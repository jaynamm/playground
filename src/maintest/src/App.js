import './App.css';
import React, {useEffect, useState} from "react";

const User = {
  id: 'skaalsdn94',
  pw: 'mandoo1229^^'
}

export default function Login(){
  const [id, setId] = useState('');
  const [pw, setPw] = useState('');

  const [idValid, setIdValid] = useState(false);
  const [pwValid, setPwValid] = useState(false);
  const [notAllow, setNotAllow] = useState(true);

  useEffect(() => {
      if(idValid && pwValid) {
        setNotAllow(false);
        return;
      }
      setNotAllow(true);
    }, [idValid, pwValid]);

  const handleId = (e) => {
      setId(e.target.value);
      const regex = /^[a-zA-Z0-9]{0,}$/;
      // const regex= /^[a-zA-Z0-9]+@[a-zA-Z0-9]+\.[A-Za-z]+$/;
      // if(regex.test(e.target.value\)){
          if (regex.test(id)) {
          setIdValid(true);
      } else{
          setIdValid(false);
      }
  };

  const handlePw = (e) => {
      setPw(e.target.value);
      const regex = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+])(?!.*[^a-zA-z0-9$`~!@$!%*#^?&\\(\\)\-_=+]).{0,20}$/;
      if (regex.test(e.target.value)) {
        setPwValid(true);
      } else {
        setPwValid(false);
      }
    };

    const onClickConfirmButton = () => {
      if(id === User.id && pw === User.pw) {
        alert('로그인에 성공했습니다.')
      } else {
        alert("등록되지 않은 회원입니다.");
      }
    }

  return (
    <div class="App">
      <lable class="Rectangle16"></lable>
      <div class="outbox"></div>
      <div class=" main">PLAY</div>
      <div class=" main2">GROUND</div>
      <div class=" serve">플레이그라운드에 오신 것을 환영합니다.</div>
      <div class=" login">로그인</div>
      <button onClick={onClickConfirmButton} disabled={notAllow} class=" playlogin ">PLAY LOGIN</button>
      <div class=" Rectingle10"></div>
      <button onclick="signup.js" className="new">회 원 가 입</button>
      <a href="https://nid.naver.com/user2/help/idInquiry?lang=ko_KR" class=" idfind">아이디 찾기</a>
      <a href="https://nid.naver.com/user2/help/pwInquiry?lang=ko_KR" class=" passwordfind">비밀번호 찾기</a>
      <div class=" i">|</div>
      <div class=" group16"></div>
      <input class=" idinput" type="text" placeholder="아이디" value={id} onChange={handleId}></input>
      <input class=" passwordinput" type="password" placeholder="비밀번호" value={pw} onChange={handlePw}></input>
      </div>
  );
  }

// export default App;



// import React, { useState } from "react";


    