import { Outlet } from "react-router-dom";
import MainNavigation from "../components/MainNavigation";
import Footer from "../components/Footer";
import ErrorPage from "./Error";
import { useRouteError } from "react-router-dom";

const RootLayout = () => {
  const error = useRouteError();
  return (
    <>
      <MainNavigation />
      {error ? (
        <ErrorPage />
      ) : (
        <main className="mt-11 pb-56 min-h-screen">
          <Outlet />
        </main>
      )}
      <Footer />
    </>
  );
};

export default RootLayout;
