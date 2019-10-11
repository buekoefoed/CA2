const showAllBtn = document.getElementById("showAllBtn");
const userInput = document.getElementById("showUser");
const showUserBtn = document.getElementById("showUserBtn");

function renderAllPersons() {
    fetch("http://localhost:8080/api/margarita/person/all")
        .then(function (response) {
            return response.json();
        })
        .then(function (data) {
            let userTable = data.map(person => {
                return `<tr><td>${person.email}</td><td>${person.firstName}</td><td>${person.lastName}</td><td>${user.phones}</td><td>${user.address}</td><td>${user.cityInfoDTO}</td><td>${user.hobbyDTOS}</td></tr>`;
            });
            userTable.unshift(`<tr><th>Email</th><th>FirstName</th><th>LastName</th><th>Phones</th><th>Address</th><th>CityInfo</th><th>HobbyDTOs</th></tr>`);
            table.innerHTML = `${userTable.join("")}`;
        })
}

showAllBtn.addEventListener('click', event => {
    renderAllPersons();
});

renderAllPersons();

