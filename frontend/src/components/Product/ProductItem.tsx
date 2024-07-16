import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import allColors from "@/assets/colorImages/colors";
import Color from "@/types/color";
import { ProductVariation, Product } from "../../types/product";

type Props = {
  product: Product;
};

type ProductColor = {
  id: number;
  value: string;
};

const ProductItem = ({ product }: Props) => {
  const [colors, setColors] = useState<Array<Color>>([]);

  useEffect(() => {
    window.scrollTo(0, 0);

    // Get the color variation
    const productColor = product.variations.find(
      (variation: ProductVariation) => variation.name === "color"
    );

    // Get all color options
    const productColorOptions = productColor?.variationOptions.map(
      (item: ProductColor) => item.value
    );

    // Filter colors that are available for this product
    const colors = allColors.filter((color) =>
      productColorOptions?.includes(color.name)
    );
    setColors(colors);
  }, [product.variations]);

  return (
    <li>
      <Link to={`/product/${product.id}`} state={{ product, colors }}>
        <div className=" flex flex-col h-[29.4117647059rem] overflow-hidden p-8 transition-all duration-300 ease-ease cursor-pointer w-72 bg-white rounded-[18px] shadow-product-card mr-5 mb-12 hover:shadow-product-card-hover hover:scale-101">
          <div className="my-0 mx-auto min-h-[13.5294117647rem] pb-0 pt-[2.4rem] w-full">
            <img
              className="block h-[13.5294117647rem] mx-auto w-auto my-0"
              src={product?.imageURLs[0]?.imagePath}
              alt=""
              width={200}
              height={200}
            />
          </div>
          <div className="flex flex-col h-[9.7647058824rem] pt-[1.176rem] relative">
            <div>
              <ul className="flex justify-center gap-2 pt-[19px] pb-[14px]">
                {colors.map((color) => (
                  <li key={color.id}>
                    <img width={12} height={12} src={color.image} alt="" />
                  </li>
                ))}
              </ul>
            </div>
            <div className="mt-0 pt-2">
              <h3 className="text text-[18px] text-primary line-clamp-2">
                {product.name}
              </h3>
            </div>
            <div className="pt-0 mt-auto ">
              <span className="text-base font-normal leading-5 tracking-wide">
                1.429.000đ
              </span>
            </div>
          </div>
        </div>
      </Link>
    </li>
  );
};

export default ProductItem;
