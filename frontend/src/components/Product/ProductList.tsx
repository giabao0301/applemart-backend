import ProductItem from "./ProductItem";
import { Product } from "../../types/product";
import { useAppSelector, useAppDispatch } from "../../redux/hooks";
import {
  fetchProducts,
  selectProducts,
} from "../../redux/features/product/productSlice";
import { useEffect } from "react";

const ProductList = () => {
  const dispatch = useAppDispatch();

  const products = useAppSelector(selectProducts);

  const productStatus = useAppSelector((state) => state.products.status);

  useEffect(() => {
    if (productStatus === "idle") {
      dispatch(fetchProducts());
    }
  }, [productStatus, dispatch]);

  return (
    <div className="">
      <ul className="grid grid-cols-4 w-full">
        {products.map((product: Product) => (
          <ProductItem key={product.id} product={product} />
        ))}
      </ul>
    </div>
  );
};

export default ProductList;
