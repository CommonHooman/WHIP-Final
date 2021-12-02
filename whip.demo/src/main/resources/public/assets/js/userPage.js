function gravar(){
    var titulo = localStorage.email;
    var div = document.getElementById("username");
        
    div.innerHTML = '<a class="nav-link">' + titulo +'</a>';
}

function getUser() {
    $.ajax({
        url: 'http://0.0.0.0:4567/credencial/' + localStorage.getItem("email"),
        //${localStorage.getItem("email")}
        type: "GET",           
        dataType: "json"
    }).done(function (data) {
        console.log(data);
        var tab = document.getElementById("mainTable");
        for(i = 0; i < data.length; i++){
            tab.innerHTML=    
                '<tr>' + 
                    '<th style="color: rgb(249,252,255);">' + data[i].site + '</th>'+
                    '<th style="color: #ffffff;">' + data[i].username + '</th>'+
                    '<th style="color: #ffffff;">' + data[i].valor + '</th>'+
                '</tr>'
        }
        //window.location.replace("/userPage.html");
        }).fail(function(error) {
    //alert( "Senha inválida" );
    })
}

function getCredencial(){
    let username = document.getElementById("username2").value;
    let valor = document.getElementById("valor").value;
    let site = document.getElementById("site").value;
    var data = { username: username, valor: valor, fk_username: localStorage.getItem("email"), site: site};
    console.log(username + " " + valor + " " + site);
    //debugger;

    $.ajax({
        url: `http://0.0.0.0:4567/credencial`,
        type: "POST",
        //data: JSON.stringify(data),
        data: data,
            headers: {                
                "Content-Type": "application/json",
            }
        //dataType: "json"
    }).done(function (data) {
        //alert(data);
        console.log("teste");
        localStorage.setItem('email', email);
        window.location.replace("/userPage.html");
    }).fail(function(error) {
    //alert( "Senha inválida" );
    })
}