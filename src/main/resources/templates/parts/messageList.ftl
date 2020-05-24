<#import "messageListItem.ftl" as mlist>

<#macro messageListForm messages>

    <div class="content" style="position:absolute; left: 1%; width: 98%">
        <div class="list-group">
            <#list messages as message>
                <@mlist.item message></@mlist.item>
            </#list>
    </div>
    <form method="post" action="${url}/additionMessages">
        <div class="text-center">
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <button  class="btn btn-outline-light" type="submit" >
                <img src="/static/picture/button-down.png">
            </button>
        </div>
    </form>
</#macro>