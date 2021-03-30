/* carousal working infinitly and displaying images */
/* to make it finite use the function clearInterval() */
var carousalList = document.getElementsByClassName("carousal-inner__item");
var listNo = document.getElementsByClassName("no");
var i = 1; /* it is global variable and it is set to 1 by default */
setInterval(updateCarousal,4000); 
function updateCarousal() {
  switch(i) {
    case 0:
      carousalList[2].classList.remove("active");
      carousalList[0].classList.add("active");
      listNo[2].classList.remove("makegolden");
      listNo[0].classList.add("makegolden");
      i++;
      break;
    case 1:
      carousalList[0].classList.remove("active");
      carousalList[1].classList.add("active");
      listNo[0].classList.remove("makegolden");
      listNo[1].classList.add("makegolden")
      i++;
      break;
    case 2:
      carousalList[1].classList.remove("active");
      carousalList[2].classList.add("active");
      listNo[1].classList.remove("makegolden");
      listNo[2].classList.add("makegolden")
      i = 0;
      break;
    default:
      alert("problem in carousal right");

  }
}
function updateCarousalLeft() {
  switch(i) {
    case 2:
      carousalList[2].classList.remove("active");
      carousalList[1].classList.add("active");
      listNo[2].classList.remove("makegolden");
      listNo[1].classList.add("makegolden")
      i--;
      break;
    case 1:
      carousalList[1].classList.remove("active");
      carousalList[0].classList.add("active");
      listNo[1].classList.remove("makegolden");
      listNo[0].classList.add("makegolden")
      i--;
      break;
    case 0:
      carousalList[0].classList.remove("active");
      carousalList[2].classList.add("active");
      listNo[0].classList.remove("makegolden");
      listNo[2].classList.add("makegolden")
      i = 2;
      break;
    default:
      alert("problem in carousal left");

  }
}

/* menu items */



function show() {
  for(j=1;j<=3;j++){
    document.getElementById("beverages-item" + j).style.display = 'flex';
    document.getElementById("spiceymeal-item" + j).style.display = 'flex';
    document.getElementById("fastfood-item" + j).style.display = 'flex';
    document.getElementById("sweets-item" + j).style.display = 'flex';
  }
}

class forMenuItem {
  constructor(id) {
    this.left = 1;
    this.right = 3;
    this.id = id;
  }
  moveLeft() {
    if(this.left>=2 && this.right<=6) {
      document.getElementById(this.id + this.right).style.display = 'none';
      this.right--;
      this.left--;
      for(j=this.left;j<=this.right;j++){
        document.getElementById(this.id + j).style.display = 'flex';
      }
    }
    else
      return;
      
  }
  moveRight() {
    if(this.left<=3 && this.right<=6) {
      document.getElementById(this.id + this.left).style.display = 'none';
      this.right++;
      this.left++;
      for(j=this.left;j<=this.right;j++){
        document.getElementById(this.id + j).style.display = 'flex';
      }
    }
    else 
      return;
  }
}

var forMenuItemBeverages = new forMenuItem('beverages-item');
var forMenuItemspiceymeal = new forMenuItem('spiceymeal-item');
var forMenuItemFastfood = new forMenuItem('fastfood-item');
var forMenuItemSweets = new forMenuItem('sweets-item');

function moveLeft (id) {
  switch(id) {
    case 'beverages-item':
      forMenuItemBeverages.moveLeft();
      break;
    case 'spiceymeal-item':
      forMenuItemspiceymeal.moveLeft();
      break;
    case 'fastfood-item':
      forMenuItemFastfood.moveLeft();
      break;
    case 'sweets-item':
      forMenuItemSweets.moveLeft();
      break;
    default:
      alert("problem in move left");
  }
}



function moveRight (id) {
  switch(id) {
    case 'beverages-item':
      forMenuItemBeverages.moveRight();
      break;
    case 'spiceymeal-item':
      forMenuItemspiceymeal.moveRight();
      break;
    case 'fastfood-item':
      forMenuItemFastfood.moveRight();
      break;
    case 'sweets-item':
      forMenuItemSweets.moveRight();
      break;
    default:
      alert("problem in move right");
  }
}

//uploading info to local storage
function writeInformation(gotUrl, gotTitle, gotPrice) {
  localStorage.setItem("gotUrl",gotUrl);
  localStorage.setItem("gotTitle",gotTitle);
  localStorage.setItem("gotPrice",gotPrice);
  window.open('orderitem.html','_blank');
}
