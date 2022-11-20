const container = document.querySelector(".container"),
    pwShowHide = document.querySelectorAll(".showHidePw"),
    pwFields = document.querySelectorAll(".password"),
    signUp = document.querySelector(".signup-link"),
    signEdit = document.querySelector(".signedit-link"),
    login = document.querySelector(".login-link"),
    updateBtn = document.querySelector(".update-btn");

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

updateBtn.addEventListener("click", ()=>{
    var passwordOrigin = document.querySelector(".pw1"),
        passwordConfirm = document.querySelector(".pw2");

    if(passwordOrigin.value != passwordConfirm.value) {
        passwordConfirm.setCustomValidity("비밀번호가 일치하지 않습니다.");
    } else {
        passwordConfirm.setCustomValidity("");
    }
});