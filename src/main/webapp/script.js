const showAllBtn = document.getElementById("showAllBtn");
const userInput = document.getElementById("showUser");
const showUserBtn = document.getElementById("showUserBtn");
const table = document.getElementById("table");
const createUserBtn = document.getElementById("createPersonBtn");
const firstNameInput = document.getElementById("firstName");
const lastNameInput = document.getElementById("lastName");
const emailInput = document.getElementById("email");
const streetInput = document.getElementById("street");
const additionalInfoInput = document.getElementById("additionalInfo");
const cityInput = document.getElementById("city");
const zipCodeInput = document.getElementById("zipCode");

function renderAllPersons() {
    fetch("http://localhost:8080/startcodeOas/api/margarita/person/all")
        .then(function (response) {
            return response.json();
        })
        .then(function (data) {
            let userTable = data.map(person => {
                return `<tr><td>${person.email}</td><td>${person.firstName}</td><td>${person.lastName}</td><td>${person.address.street} ${person.address.additionalInfo}</td><td>${person.cityInfoDTO.city} ${person.cityInfoDTO.zipCode}</td></tr>`;
            });
            userTable.unshift(`<tr><th>Email</th><th>First name</th><th>Last name</th><th>Address</th><th>City info</th></tr>`);
            table.innerHTML = `${userTable.join("")}`;
        })
}

createUserBtn.addEventListener('click', event => {
    event.preventDefault();
    let data =
        {
            firstName: firstNameInput.value,
            lastName: lastNameInput.value,
            email: emailInput.value,
            phones: [],
            address: {
                street: streetInput.value,
                additionalInfo: additionalInfoInput.value,
            },
            cityInfoDTO: {
                city: cityInput.value,
                zipCode: zipCodeInput.value,
            },
            hobbies: []
        };

    console.log(data);

    let options = makeOptions("POST", data);
    fetch("http://localhost:8080/startcodeOas/api/margarita/person/", options)
        .then(function (response) {
            if (!response.ok) {
                alert(response.status);
                return;
            }
            return response.json();
        })
        .then(function (user) {
            let table = document.getElementById("createPersonResponse");
            table.innerHTML = `<tr><td>${user.firstName}</td><td>${user.lastName}</td><td>${user.email}</td><td>${user.address.street} ${user.address.additionalInfo}</td><td>${user.cityInfoDTO.city} ${user.cityInfoDTO.zipCode}</td></tr>`
        });
});

function makeOptions(method, body) {
    let opts = {
        method: method,
        headers: {
            "Content-type": "application/json"
        }
    };
    if (body) {
        opts.body = JSON.stringify(body);
    }
    return opts;
}

showAllBtn.addEventListener('click', event => {
    renderAllPersons();
});

renderAllPersons();