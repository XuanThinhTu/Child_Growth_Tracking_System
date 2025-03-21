import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "./index.css";
import AppRouter from "./routes/index";
import { Toaster } from "react-hot-toast";

createRoot(document.getElementById("root")).render(
  <>
    <AppRouter />
    <Toaster />
  </>
);
