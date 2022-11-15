const container = document.querySelector(".container"),
      pwShowHide = document.querySelectorAll(".showHidePw"),
      pwFields = document.querySelectorAll(".password"),
      signUp = document.querySelector(".signup-link"),
      login = document.querySelector(".login-link"),
      passwordOrigin = document.querySelector(".pw1"),
      passwordConfirm = document.querySelector(".pw2"),
      signupBtn = document.querySelector(".signup-btn");

    //  아이콘 비밀번호 숨기기
    pwShowHide.forEach(eyeIcon =>{
        eyeIcon.addEventListener("click", ()=>{
            pwFields.forEach(pwField =>{
                if(pwField.type ==="password"){
                    pwField.type = "text";

                    pwShowHide.forEach(icon =>{
                        icon.classList.replace("uil-eye-slash", "uil-eye");
                    })
                }else{
                    pwField.type = "password";

                    pwShowHide.forEach(icon =>{
                        icon.classList.replace("uil-eye", "uil-eye-slash");
                    })
                }
            }) 
        })
    })

    // 로그인 폼 < = > 등록 폼 
    signUp.addEventListener("click", ( )=>{
        container.classList.add("active");
    });

    login.addEventListener("click", ( )=>{
        container.classList.remove("active");
    });

    signupBtn.addEventListener("click", ()=>{
        if(passwordOrigin.value != passwordConfirm.value) {
            passwordConfirm.setCustomValidity("비밀번호가 일치하지 않습니다.");
        }
    });
