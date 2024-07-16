import { useParams } from "react-router-dom";
import Categories from "./Categories";
import ProductList from "./product/ProductList";

const HomePage = () => {
  const params = useParams();
  console.log(params.category);

  return (
    <div className="mx-36 my-0">
      <div className="max-w-[780px] pt-20 pb-16 px-0 min-h-60">
        <h1 className="inline text-5xl font-semibold leading-tight tracking-tight mr-2">
          Apple Mart.
        </h1>
        <div className="inline text-5xl font-semibold leading-tight tracking-tight text-secondary">
          Nơi tuyệt vời để mua sản phẩm công nghệ mà bạn thích.
        </div>
      </div>
      <Categories />
      <ProductList />
    </div>
  );
};

export default HomePage;
