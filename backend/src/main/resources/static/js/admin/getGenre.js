$(document).ready(function() {
  $.ajax({
    type: 'GET',
    url: '/api/v1/genre',
    success: function(genres) {
      genres.forEach(function(genre) {
        console.log("genre.genreName",genre.genreName);
        console.log("genre.toString",genre.toString());
        $('#genreContainer').append(
            `<div class="form-check">
            <input class="form-check-input" type="checkbox" name="genreName[]" value="${genre.genreName}" id="genre-${genre.genreName}">
            <label class="form-check-label" for="genre-${genre.genreName}">${genre.genreName}</label>
          </div>`
        );
      });
    },
    error: function(error) {
      console.error("Error loading genres:", error);
    }
  });
});
