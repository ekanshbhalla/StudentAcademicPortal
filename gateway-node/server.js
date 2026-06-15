const express = require("express");
const cors = require("cors");
const axios = require("axios");
require("dotenv").config();

const app = express();

const PORT = process.env.PORT || 5000;
const HOST = process.env.HOST || "0.0.0.0";
const SPRING_BOOT_URL = process.env.SPRING_BOOT_URL || "http://localhost:8081";
const CORS_ORIGIN = process.env.CORS_ORIGIN || "*";

const backend = axios.create({
  baseURL: SPRING_BOOT_URL,
  timeout: Number(process.env.PROXY_TIMEOUT_MS || 15000)
});

app.use(cors({
  origin: CORS_ORIGIN === "*" ? "*" : CORS_ORIGIN.split(",").map((origin) => origin.trim()),
  credentials: CORS_ORIGIN !== "*"
}));
app.use(express.json());

app.get("/", (req, res) => {
  res.json({
    message: "Academic Planning Node Gateway Running",
    backend: SPRING_BOOT_URL
  });
});

app.get("/health", (req, res) => {
  res.json({
    status: "UP",
    service: "gateway-node"
  });
});

const forwardRequest = async (req, res) => {
  try {
    const response = await backend.request({
      method: req.method,
      url: req.path,
      data: req.body,
      params: req.query,
      headers: {
        Authorization: req.get("Authorization") || "",
        "Content-Type": req.get("Content-Type") || "application/json"
      },
      validateStatus: () => true
    });

    res.status(response.status).json(response.data);
  } catch (error) {
    const status = error.response?.status || 502;
    const message = error.response?.data || {
      message: "Gateway failed to reach Spring Boot backend"
    };

    res.status(status).json(message);
  }
};

const routes = [
  "/auth/login",
  "/auth/signup",
  "/students/all",
  "/students/add",
  "/courses/all",
  "/courses/add",
  "/enrollments/all",
  "/enrollments/add",
  "/prerequisites/all",
  "/prerequisites/add"
];

routes.forEach((route) => {
  app.all(route, forwardRequest);
});

app.use((req, res) => {
  res.status(404).json({
    message: "Gateway route not found"
  });
});

app.listen(PORT, HOST, () => {
  console.log(`Gateway running on http://${HOST}:${PORT}`);
  console.log(`Forwarding API requests to ${SPRING_BOOT_URL}`);
});
