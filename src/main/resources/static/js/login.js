(async () => {

    const {value: formValues} = await Swal.fire({
        title: 'Please, put data',
        type: "input",
        html:
            '<form action="/hello" method="post">' +
            '<input id="login" placeholder="Login" class="swal2-input">' +
            '<input id="password" placeholder="Password" class="swal2-input">' +
            '<button type="submit" ' +
            'class="swal2-confirm swal2-styled" aria-label="" ' +
            'style="display: inline-block; border-left-color: rgb(48, 133, 214);' +
            ' border-right-color: rgb(48, 133, 214);">' +
            'OK' +
            '</button> ' +
            '</form>',
        focusConfirm: false,
        showConfirmButton: false,
        closeOnConfirm: false,
        preConfirm: () => {
            return [
                document.getElementById('login').value,
                document.getElementById('password').value
            ]
        },
    })
})()

<!-- Sweet alert -->
<script src="/static/sweetAlert/dist/sweetalert2.all.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@9"></script>
    <!-- Optional: include a polyfill for ES6 Promises for IE11 -->
    <script src="https://cdn.jsdelivr.net/npm/promise-polyfill"></script>

    <script src="/static/sweetAlert/dist/sweetalert2.min.js"></script>
    <link rel="stylesheet" href="/static/sweetAlert/dist/sweetalert2.min.css">