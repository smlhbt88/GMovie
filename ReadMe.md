
GMovie API Specification
========================

Resource Summary
----------------

URI                                                     HTTPMethod   HTTP Status    Description
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
galvanize.com/gmovie/movies	                              GET	        200 OK	    Return a list of movies.
***************************************************************************************************************************************************************************************************
galvanize.com/gmovie/movies/?title={title}	              GET 	        200 OK  	Returns a movie when searched by title. If move not exists return a message "Movie does not exist"
****************************************************************************************************************************************************************************************************
galvanize.com/gmovie/movies/?id={id}&?rating={rate}	      PATCH	        200 OK	    Return a movie with review and rating with average rating. If rating is zero, return a message "Rating is required"
****************************************************************************************************************************************************************************************************
galvanize.com/gmovie/movies/review/?id={id}&rate={rate}   PATCH         200 OK      Return movie details with review. If no rating provided a friendly message "Rating is required" shows
*****************************************************************************************************************************************************************************************************


| URI                           | HTTPMethod     | HTTP Status     |Description|
| :---------------------------  | :----------: | -----------: |
|  galvanize.com/gmovie/movies | GET   | 200 OK    |Return a list of movies.
| galvanize.com/gmovie/movies/?id={id}&?rating={rate}   | GET | 200 OK |Returns a movie when searched by title. If move not exists return a message "Movie does not exist"







