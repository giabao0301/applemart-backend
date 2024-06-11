import Footer from "../components/Footer";
import MainNavigation from "../components/MainNavigation";

export default function ErrorPage() {
  return (
    <>
      <MainNavigation />
      <main className="mt-11">
        <h1 className="text-5xl font-semibold w-[600px] mt-24 mx-auto mb-13 leading-tight tracking-tight text-center">
          The page you’re looking for can’t be found.
        </h1>
      </main>
      <Footer />
    </>
  );
}
