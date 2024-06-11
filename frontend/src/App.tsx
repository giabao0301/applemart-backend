import "./index.css";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import ErrorPage from "./pages/Error.tsx";
import RootLayout from "./pages/Root.tsx";
import HomePage from "./components/Home.tsx";
import SignupForm from "./pages/SignupForm.tsx";
import LoginForm from "./pages/LoginForm.tsx";
import CartPage from "./pages/Cart.tsx";
import ProductDetail from "./components/Product/ProductDetail.tsx";

const router = createBrowserRouter([
  {
    path: "/",
    element: <RootLayout />,
    errorElement: <ErrorPage />,
    children: [
      {
        index: true,
        element: <HomePage />,
      },
      {
        path: "account",
        element: <SignupForm />,
      },
      {
        path: "login",
        element: <LoginForm />,
      },
      {
        path: "cart",
        element: <CartPage />,
      },
      {
        path: ":category",
        element: <HomePage />,
      },
      {
        path: "product/:id",
        element: <ProductDetail />,
      },
    ],
  },
]);

function App() {
  return <RouterProvider router={router} />;
}

export default App;
