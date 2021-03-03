<#macro menuDep>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
            integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
            integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
          crossorigin="anonymous">
    <link rel="stylesheet" href="/static/css/style.css">
</#macro>
</head>

<body>
<#macro menu>
    <div class="content" id="container" style="background-color: white; position: fixed; left: 68%; bottom: 1%;  ">
        <form method="post" action="/sendMessage"  enctype="multipart/form-data">
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <div class="content" >
                <div class="head">
                    <a class="btn-close trigger" href="#">
                        <button type="button" class="close" aria-label="Close">
                            <span aria-hidden="true" style="color: #e9f0ef">&times;</span>
                        </button>
                    </a>
                    <p class="text-center"> Send notes </p>
                </div>
                <div class="content">
                    <div class="form-group">
                        <textarea class="form-control" name="to" rows="1" placeholder="Recipient"></textarea>
                    </div>
                    <div class="form-group">
                        <textarea class="form-control" name="subject" rows="1" placeholder="Subject"></textarea>
                    </div>
                    <div class="form-group">
                        <textarea class="form-control" name="body" rows="14"></textarea>
                    </div>
                </div>
                <div class="footer" style="background-color: white">

                    <div class="form-inline">
                        <div class="form-group">
                            <button type="submit" class="btn btn-outline-light"><strong>Send</strong></button>
                            <div class="custom-file" style="width: 10%" >
                                <input type="file" name="attachedFiles" id="attachedFiles">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</#macro>
</body>
</html>
