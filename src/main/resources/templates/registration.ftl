<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
    <div class="mb-1 text-center"> Add new user </div>
    ${notes!}
    <@l.login "/registration" true />
</@c.page>