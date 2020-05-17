<#macro login path isRegistrationForm>
    <form action="${path}" method="post">
        <#if isRegistrationForm>
            <div class="form-group row ">
                <label class="col-sm-2 col-form-label">Email:</label>
                <div class="col-sm-6">
                    <input type="email" name="email" class="form-control" placeholder="email@domail.com" />
                </div>
            </div>
        </#if>
        <div class="form-group row ">
            <label class="col-sm-2 col-form-label">User Name :</label>
            <div class="col-sm-6">
                <input type="text" name="username" class="form-control" placeholder="User name" />
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Password:</label>
            <div class="col-sm-6">
                <input type="password" name="password" class="form-control" placeholder="Password" />
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <#if !isRegistrationForm><p class="text-center"> You have't got account? <a   href="/registration"> register now!</a></p><br></#if>
        <button class="btn btn-primary" type="submit"> <#if isRegistrationForm> Create <#else> Sigh In </#if></button>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button class="btn btn-primary" type="submit" value="Sign Out"> Sign Out </button>
    </form>
</#macro>