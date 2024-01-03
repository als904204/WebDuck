$(document).ready(function() {
  $.ajax({
    type: 'GET',
    url: '/api/v1/genre',
    success: function(genres) {
      genres.forEach(function(genre) {
        $('#genreContainer').append(
            `<div class="form-check">
            <input class="form-check-input" type="checkbox" value="${genre.genreType}" id="genre-${genre.genreType}">
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