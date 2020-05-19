document.querySelector(".first").addEventListener('click', function(){
    swal("Our First Alert");
});

document.querySelector(".second").addEventListener('click', function(){
    swal("Our First Alert", "With some body text!");
});

document.querySelector(".third").addEventListener('click', function(){
    swal("Our First Alert", "With some body text and success icon!", "success");
});

const Toast = Swal.mixin({
    toast: true,
    position: 'top-end',
    showConfirmButton: false,
    timer: 3000,
    timerProgressBar: true,
    onOpen: (toast) => {
        toast.addEventListener('mouseenter', Swal.stopTimer)
        toast.addEventListener('mouseleave', Swal.resumeTimer)
    }
})

Toast.fire({
    icon: 'success',
    title: 'Signed in successfully'
})