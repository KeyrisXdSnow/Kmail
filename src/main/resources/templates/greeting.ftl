<#import "parts/common.ftl" as common>
<@common.page>
    <div class="row justify-content-md-center">
        <#if errorAAA??>
            <img src="/static/picture/error.png">
            <#else> <img src="/static/picture/valera.png">
        </#if>
   </div>
    <div class="footer mt-5 justify-content-center" >
        <p><h1 class="text-center">It's post Service KMail</h1></p>
    </div>

</@common.page>