var mobileNav = document.getElementsByClassName("mobile-navbar");
var backDrop = document.getElementsByClassName("backdrop");
var noOfClick = 1;
/* showing mobile nav */
function showMobileNav() {
  if(noOfClick % 2 != 0){
    mobileNav[0].classList.add("display-flex");
    backDrop[0].classList.add("display-block");
  }
  else if(noOfClick % 2 == 0) {
    mobileNav[0].classList.remove("display-flex");
    backDrop[0].classList.remove("display-block")
  }
  noOfClick++;
}

// password validation
function check() {
    var password = document.getElementById('password');
    var conPassword = document.getElementById('confirm-password');
    var button = document.getElementById("submit");
    if(password && conPassword) {
    if (password.value != conPassword.value) {
      conPassword.style.backgroundColor = 'rgba(240, 130, 130, 0.753)';

      button.setAttribute("disabled","disabled");
    }
    else {
      conPassword.style.backgroundColor = 'rgba(255, 255, 255, 0.562)';
      button.removeAttribute("disabled");
    }
  }
}