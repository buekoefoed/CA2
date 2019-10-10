const urlOpenApi = "openapi";
const urlGithubProject = "https://github.com/emilgth/CA-1_Margarita_Systems";
const urlTravisProject = "https://travis-ci.org/buekoefoed/CA2/";
const table = "<table class='table table-hover rounded' style='background-color: white'><thead class='rounded'><tr>";
const ths = "<th>Name</th><th>Student ID</th><th>Color (green, yellow, red)</th></tr></thead><tbody>";
const OpenGithubButton = document.getElementById("jokeIdInput");
const OpenApiButton = document.getElementById("API-documentation");
const OpenTravisButton = document.getElementById("getJokeById");

OpenApiButton.addEventListener("click", function OpenApiButton() {
    fetch (urlOpenApi + JokeIdInput.value)
        .then(res => res.json())
        .then(data => tableDiv.innerHTML = table + ths3 + '<tr>' +
            '<td>' + data.theJoke + '<td/>' +
            '<body/><table/>'  );
});


