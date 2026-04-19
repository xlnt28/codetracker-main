# Leave Classroom Endpoint

## Endpoint

- **Method:** `PUT`
- **Path:** `/api/classrooms/{classroomId}/leave`
- **Auth:** Required (`Authorization: Bearer <access_token>`)
- **Body:** None

## Purpose

Allows a student to leave a classroom **without deleting** their enrollment row.
Instead, the backend updates student enrollment state:

- `status` becomes `DROPPED`
- `leftAt` is set to the current time

If the student joins again later, the same enrollment is reactivated and `leftAt` is cleared.

## Success Response

- **Status:** `200 OK`
- **Body:**

```json
{
  "message": "Successfully left classroom."
}
```

## Error Responses

### Classroom not found

- **Status:** `404 Not Found`
- **Body:**

```json
{
  "error": "Classroom not found."
}
```

### User not in classroom

- **Status:** `404 Not Found`
- **Body:**

```json
{
  "error": "User is not part of this classroom."
}
```

### User already left

- **Status:** `400 Bad Request`
- **Body:**

```json
{
  "error": "User has already left this classroom."
}
```

## Example cURL

```bash
curl -X PUT "https://your-domain.com/api/classrooms/{classroomId}/leave" \
  -H "Authorization: Bearer <access_token>"
```

## Rejoin Flow

1. Student joins classroom.
2. Student leaves classroom (`leftAt` is set).
3. Student joins again (`leftAt` is reset to `null`).
