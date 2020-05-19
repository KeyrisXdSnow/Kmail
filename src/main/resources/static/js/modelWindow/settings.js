var modal = document.querySelector("#setting-modal");
var modalOverlay = document.querySelector("#modal-overlay");
var openButton = document.getElementById("open-button");

openButton.addEventListener("click", function() {
    modal.classList.toggle("closed");
    modalOverlay.classList.toggle("closed");
});

$(modalOverlay).on('click', function (e) {
    if (e.target == this){
        modal.classList.toggle("closed");
        modalOverlay.classList.toggle("closed");
    }
})