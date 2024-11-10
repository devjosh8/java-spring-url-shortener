
# URL-Shortener

A small URL-Shortener built using Java Spring Boot.
This project was created for the **URL-Shortening-Service** Project by https://roadmap.sh.

### Project link:

https://roadmap.sh/projects/url-shortening-service

## Usage

### Create shortened URL

Create a new shortened URL using the post method. Pass in your url as as the `url` argument.

```
POST /shorten
{
   "url": "YOUR_URL_TO_SHORTEN_HERE.com"
}
```

The returned body will look something like this:

```
{
    "id": 1,
    "url": "https://www.youtube.com/watch?v=dQw4w9WgXcQ",
    "shortCode": "HxOvELM20Y",
    "createdAt": "2024-11-10 17:17:51",
    "updatedAt": "2024-11-10 17:17:51"
}
```
where `shortCode` is the shortened url.

### Retrieve original URL

Retrieve the original url using the `shortCode`.

```
GET /shorten/HxOvELM20Y
```

which will return the following body:

```
{
    "id": 1,
    "url": "https://www.youtube.com/watch?v=dQw4w9WgXcQ",
    "shortCode": "HxOvELM20Y",
    "createdAt": "2024-11-10 17:17:51",
    "updatedAt": "2024-11-10 17:17:51"
}
```

### Update Short URL

Update original URL of a short Code. Use a put statement with the following body:

```
PUT /shorten/HxOvELM20Y
{
  "url": "https://google.com"
}
```
The short code `HxOvELM20Y` will now redirect to `https://google.com`.

The returned body will be the updated status:

```
{
    "id": 1,
    "url": "https://google.com",
    "shortCode": "HxOvELM20Y",
    "createdAt": "2024-11-10 17:17:51",
    "updatedAt": "2024-11-10 17:23:32"
}
```

### Delete Short URL

Delete an existing Short URL using `DELETE` and the short Code to be deleted.

```
DELETE /shorten/HxOvELM20Y
```

This will return `HTTP 204 NO CONTENT` status when successful.

### Get URL statistics

Use this to get amount of get requests from a short Code:

```
GET /shorten/HxOvELM20Y/stats
```

this will return a body which will look something like this:

```
{
  "id": "1",
  "url": "https://google.com",
  "shortCode": "HxOvELM20Y",
  "createdAt": "2021-09-01T12:00:00Z",
  "updatedAt": "2021-09-01T12:00:00Z",
  "accessCount": 12
}
```

where `accessCount` is the amount of accesses the short code has.

## Errors

Requests will either return `HTTP 404 NOT FOUND` or `HTTP 400 BAD REQUEST` when unsuccessful.

A error description is provided.
