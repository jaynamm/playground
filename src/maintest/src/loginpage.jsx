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
        const regex = /^[a-zA-Z0-9]{4,}$/;
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
        const regex = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+])(?!.*[^a-zA-z0-9$`~!@$!%*#^?&\\(\\)\-_=+]).{8,20}$/;
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
          alert("아이디 또는 비밀번호가 일치하지 않습니다.");
        }
      }

    return(
        <div className="page">
            <div className="contenWrap">
                <div className="inputTitle">아이디</div>
                <div className='inputWrap'>
                    <input className='input'
                    placeholder="아이디"
                    value={id}
                    onChange={handleId}/>
                </div>
                <div className="errorMessageWrap">
                {!idValid && id.length > 0 && (
              <div>올바른 아이디를 입력해주세요.</div>
            )}
                    
                </div>

                <div className="inputTitle">비밀번호</div>
                <div className="inputWrap">
                    <input className="input"
                    placeholder="비밀번호"
                    type="password"
                    value={pw}
                    onChange={handlePw}/>
                </div>
                <div className="errorMessageWrap">
                {!pwValid && pw.length > 0 && (
              <div>영문, 숫자, 특수문자 포함 8자 이상 입력해주세요.</div>
            )}
                </div>
            </div>
            <div>
                <button onClick={onClickConfirmButton} disabled={notAllow} className="bottomButton">
                    PLAYLOGIN
                </button>
            </div>
        </div>
    );
}




