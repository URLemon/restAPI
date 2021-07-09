

function clickUser() {
    $('#divTable').show();
    $('#divFromAdd').hide();
    $('#table').empty();
    $('#btn-admin').toggleClass("btn-primary", "btn-default");
    $('#btn-user').toggleClass("btn-primary", "btn-default");
    $('#panel').hide();
    $('#column_edit').hide();
    $('#column_delete').hide();
    $('#namePage').text("User information-page");
    $('#nameTable').text("About user")
    getInfoAccount();
}

function clickAdmin() {
    $('#divTable').show();
    $('#divFromAdd').hide();
    $('#table').empty();
    $('#btn-user').toggleClass("btn-primary", "btn-default");
    $('#btn-admin').toggleClass("btn-primary", "btn-default");
    $('#panel').show();
    $('#column_edit').show();
    $('#column_delete').show();
    $('#namePage').text('Admin panel');
    $('#nameTable').text('All users');
    $('#vklad1').addClass('active');
    $('#vklad2').removeClass('active');
    getAllUsers();
}

function clickAddNewUser(){
    $('#divTable').hide();
    $('#divFromAdd').show();
    $('#vklad2').addClass('active');
    $('#vklad1').removeClass('active');
}

function clickUsersTable(){
    $('#divTable').show();
    $('#divFromAdd').hide();
    $('#vklad1').addClass('active');
    $('#vklad2').removeClass('active');
}

async function sendUser(){
    //e.preventDefault();


    let headers = new Headers();
    headers.append('Content-Type', 'application/json; charset=utf-8');

    let user = {
        'firstName': $('#inputFirstName').val(),
        'lastName': $('#inputLastName').val(),
        'age':$('#inputAge').val(),
        'email': $('#inputEmail').val(),
        'password': $('#inputPassword').val(),
        'roles': $('#newRole').val().map(role => {
            n = role.split(' ');
            console.log(n);
            return {
                'id': n[0],
                'role' : n[1]
            };
        })
    };

    let request = new Request('/admin/saveUser/', {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(user)
    });

    console.log(request);
    fetch(request).then(
        function (response){
            if(response.ok){
                clickUser();
                clickAdmin();
            }
        }
    )


}
function clickDelete(id){
    $('#modalDelete').modal('show');
    fetch(`/admin/get?id=${id}`).then(function (response) {
        if (response.ok) {
            response.json().then((user) => {
                console.log(user.id);
                $('#deleteId').val(user.id);
                $('#deleteFirstName').val(user.firstName);
                $('#deleteLastName').val(user.lastName);
                $('#deleteAge').val(user.age);
                $('#deleteEmail').val(user.email);
                $('#buttonDelete').attr("onclick", `deleteUser(${user.id})`);
            });
        }
    });
}

function clickEdita(id){
    $('#modalEdit').modal('show');
    fetch(`/admin/get?id=${id}`).then(function (response) {
        if (response.ok) {
            response.json().then((user) => {
                console.log(user.id);
                $('#editID').val(user.id);
                $('#editFirstName').val(user.firstName);
                $('#editLastName').val(user.lastName);
                $('#editAge').val(user.age);
                $('#editEmail').val(user.email);
            });
        }
    });
}

function updateUser(){
    $('#modalEdit').modal('hide');
    let headers = new Headers();
    headers.append('Content-Type', 'application/json; charset=utf-8');

    let user = {
        'id': $('#editID').val(),
        'firstName': $('#editFirstName').val(),
        'lastName': $('#editLastName').val(),
        'age':$('#editAge').val(),
        'email': $('#editEmail').val(),
        'password': $('#editPassword').val(),
        'roles': $('#editNewRole').val().map(role => {
            n = role.split(' ');
            console.log(n);
            return {
                'id': n[0],
                'role' : n[1]
            };
        })
    };

    let request = new Request('/admin/saveUser/', {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(user)
    });

    console.log(request);
    fetch(request).then(
        function (response){
            if(response.ok){
                clickUser();
                clickAdmin();
            }
        }
    )
}

async function deleteUser(id){
    $('#modalDelete').modal('hide');
    await fetch(`admin/delete?id=${id}`);
    $('#table').empty();
    getAllUsers();
}

function getAllUsers() {
    fetch('admin/users').then(function (response) {
        if (response.ok) {
            response.json().then((users) => {
                users.forEach((user) => {
                    appendUserRow(user);
                    $(`#table #c${user.id}`)
                        .append($('<td>')
                            .append($(`<button class="btn btn-info text-white" onclick="clickEdita(${user.id})">`).text("Edit")))
                        .append($('<td>')
                            .append($(`<button class="btn btn-danger" onclick="clickDelete(${user.id})">`).text("Delete")));
                });
            });
        }
    });
}



function getInfoAccount() {
    fetch('/user/give').then(function (response) {
        if (response.ok) {
            response.json().then((user) => {
                //console.log(user);
                appendUserRow(user);
            });
        }
    });
}

function appendUserRow(user) {
    $('#table')
        .append($('<tr class="border-top bg-light">').attr('id', 'c' + user.id)
            .append($('<td>').text(user.id))
            .append($('<td>').text(user.firstName))
            .append($('<td>').text(user.lastName))
            .append($('<td>').text(user.age))
            .append($('<td>').text(user.email))
            .append($('<td>').text(user.roles.map(role => role.role.substr(5))))
        );
}