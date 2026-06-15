from fastapi import FastAPI, Request, Response
from fastapi.middleware.cors import CORSMiddleware
from fastapi.responses import JSONResponse
from dotenv import load_dotenv
import os
import requests

load_dotenv()

app = FastAPI()

SPRING_BOOT_URL = os.getenv(
    "SPRING_BOOT_URL",
    "http://localhost:8081"
)

REQUEST_TIMEOUT_SECONDS = int(
    os.getenv("REQUEST_TIMEOUT_SECONDS", "15")
)

CORS_ORIGINS = [
    origin.strip()
    for origin in os.getenv("CORS_ORIGINS", "*").split(",")
]

# ---------------- CORS ----------------

app.add_middleware(
    CORSMiddleware,
    allow_origins=CORS_ORIGINS,
    allow_credentials=CORS_ORIGINS != ["*"],
    allow_methods=["*"],
    allow_headers=["*"],
)


def forward_request(
        method: str,
        path: str,
        request: Request,
        payload: dict | None = None
) -> Response:

    headers = {}
    authorization = request.headers.get("Authorization")

    if authorization:
        headers["Authorization"] = authorization

    try:
        response = requests.request(
            method,
            f"{SPRING_BOOT_URL}{path}",
            json=payload,
            params=dict(request.query_params),
            headers=headers,
            timeout=REQUEST_TIMEOUT_SECONDS
        )

        try:
            content = response.json()
        except ValueError:
            content = {
                "message": response.text
            }

        return JSONResponse(
            status_code=response.status_code,
            content=content
        )

    except requests.RequestException as exc:
        return JSONResponse(
            status_code=502,
            content={
                "message": "Gateway failed to reach Spring Boot backend",
                "detail": str(exc)
            }
        )


# ---------------- HOME ----------------

@app.get("/")
def home():
    return {
        "message": "FastAPI Gateway Running",
        "backend": SPRING_BOOT_URL
    }


@app.get("/health")
def health():
    return {
        "status": "UP",
        "service": "fastapi-gateway"
    }


# ==================================================
# AUTH
# ==================================================

@app.post("/auth/login")
def login(auth_request: dict, request: Request):
    return forward_request(
        "POST",
        "/auth/login",
        request,
        auth_request
    )


@app.post("/auth/signup")
def signup(user: dict, request: Request):
    return forward_request(
        "POST",
        "/auth/signup",
        request,
        user
    )


# ==================================================
# STUDENTS
# ==================================================

@app.get("/students/all")
def get_students(request: Request):
    return forward_request(
        "GET",
        "/students/all",
        request
    )


@app.post("/students/add")
def add_student(student: dict, request: Request):
    return forward_request(
        "POST",
        "/students/add",
        request,
        student
    )


# ==================================================
# COURSES
# ==================================================

@app.get("/courses/all")
def get_courses(request: Request):
    return forward_request(
        "GET",
        "/courses/all",
        request
    )


@app.post("/courses/add")
def add_course(course: dict, request: Request):
    return forward_request(
        "POST",
        "/courses/add",
        request,
        course
    )


# ==================================================
# ENROLLMENTS
# ==================================================

@app.get("/enrollments/all")
def get_enrollments(request: Request):
    return forward_request(
        "GET",
        "/enrollments/all",
        request
    )


@app.post("/enrollments/add")
def add_enrollment(enrollment: dict, request: Request):
    return forward_request(
        "POST",
        "/enrollments/add",
        request,
        enrollment
    )


# ==================================================
# PREREQUISITES
# ==================================================

@app.get("/prerequisites/all")
def get_prerequisites(request: Request):
    return forward_request(
        "GET",
        "/prerequisites/all",
        request
    )


@app.post("/prerequisites/add")
def add_prerequisite(prerequisite: dict, request: Request):
    return forward_request(
        "POST",
        "/prerequisites/add",
        request,
        prerequisite
    )
