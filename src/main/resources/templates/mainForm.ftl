<#import "parts/common.ftl" as c>

<@c.page>
    <div class="form-row">
        <div class="form-group col-md-6">
            <form method="get" action="mainForm" class="form-inline">
                <input type="text" name="filter" class="form-control" value="${filter!}" placeholder="Search by tag">
                <button type="submit" class="btn btn-dark ml-2">Search</button>
            </form>
        </div>
    </div>

    <a class="btn btn-dark" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false"
       aria-controls="collapseExample">
        Add new notes
    </a>
    <div class="collapse" id="collapseExample">
        <div class="form-group mt-3">
            <form method="post" action="add" enctype="multipart/form-data">
                <div class="form-group">
                    <input type="text" class="form-control" name="text" placeholder="Введите сообщение"/>
                </div>
                <div class="form-group">
                    <input type="text" class="form-control" name="tag" placeholder="Тэг">
                </div>
                <div class="form-group">
                    <div class="custom-file">
                        <input type="file" name="file" id="customFile" style="display: none">
                        <label class="custom-file-label" for="customFile"></label>
                    </div>
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <div class="form-group">
                    <button type="submit" class="btn btn-dark"> Add</button>
                </div>
            </form>
        </div>
    </div>

    <#if messages??>
        <div class="card-columns">
            <#list messages as notes>
                <div class="card my-3">
                    <#if notes.fileName??>
                        <img src="/img/${notes.fileName}" class="card-img-top" alt="">
                    </#if>
                    <div class="m-2">
                        <span>${notes.text}</span>
                        <br>
                        <i> tag: ${notes.tag}</i>

                    </div>
                    <div class="card-footer text-muted">
                        <div class="row" style="justify-content: space-between">
                            <div>${notes.authorName}</div>
                            <form method="get" action="/deleteNote">
                                <button class="btn btn-outline-light">
                                    <img src="/static/picture/close.png" alt=Delete">
                                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                    <input type="hidden" name="notesId" value="${notes.getId()}"/>
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            <#else>
                No notes
            </#list>
        </div>
    </#if>
</@c.page>