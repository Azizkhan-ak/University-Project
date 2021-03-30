function rewriteOrder(){
  var infoUrl = localStorage.getItem("gotUrl");
  var infoTitle = localStorage.getItem("gotTitle");
  var infoPrice = localStorage.getItem("gotPrice");
  var formTitleInput = document.getElementById("form-title");
  var formPriceInput = document.getElementById("form-price");

  var itemImage = document.getElementById("imagetag-Id");
  if(itemImage)itemImage.src = infoUrl;

  var itemTitle = document.getElementById("ordered-title");
  if(itemTitle){
    itemTitle.innerHTML = infoTitle;
    formTitleInput.value = infoTitle;
  }

  var itemPrice = document.getElementById("ordered-price");
  if(itemPrice){
    itemPrice.innerHTML = infoPrice;
    formPriceInput.value = infoPrice;
  }
}

