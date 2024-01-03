$(document).ready(function() {
  $.ajax({
    type: 'GET',
    url: '/api/v1/genre',
    success: function(genres) {
      genres.forEach(function(genre) {
        console.log("genre.genreType",genre.genreType);
        console.log("genre.toString",genre.toString());
        $('#genreContainer').append(
            `<div class="form-check">
            <input class="form-check-input" type="checkbox" name="genreType[]" value="${genre.genreType}" id="genre-${genre.genreType}">
            <label class="form-check-label" for="genre-${genre.genreType}">${genre.genreType}</label>
          </div>`
        );
      });
    },
    error: function(error) {
      console.error("Error loading genres:", error);
    }
  });
});
