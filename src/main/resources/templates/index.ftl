<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Flat Modal Window</title>

        <link rel='stylesheet prefetch'
              href='https://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css'>

        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
                integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
                crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
                integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
                crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
                integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy
                " crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
              integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
              crossorigin="anonymous">
        <link rel="stylesheet" href="/static/css/style.css">

    </head>

    <body>
        <!-- Button -->
        <div class="page-wrapper">
            <a class="btn trigger" href="#">Compose</a>
        </div>

        <!-- Modal -->
        <div class="modal-wrapper">
            <div class="modal">
                <div class="head">
                    <a class="btn-close trigger" href="#">
                        <i class="fa fa-times" aria-hidden="true"></i>
                    </a>
                </div>
                <div class="content">
                    <form class="form-group column">
                        <div class="form-group">
                            <textarea class="form-control" id="sendTo" rows="1" placeholder="Recipient"></textarea>
                        </div>
                        <div class="form-group">
                            <textarea class="form-control" id="subject" rows="1" placeholder="Subject"></textarea>
                        </div>
                        <div class="form-group">
                            <textarea class="form-control" id="notes" rows="14"></textarea>
                        </div>
                    </form>
                </div>
                <div class="footer">

                    <div class="form-inline">
                        <div class="form-group">
                            <button type="button" class="btn btn-outline-light"><strong>Send</strong></button>
                        </div>
                        <div class="form-group">
                            <button type="button" class="btn btn-outline-light" style="width: 11%">
                                <input type="file" hidden >
                                <img src="/static/picture/attachment.png" width="80%">
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src='https://code.jquery.com/jquery-2.2.4.min.js'></script>


        <script src="../static/js/index.js"></script>
    </body>
</html>