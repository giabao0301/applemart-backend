import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { ProductVariation } from "../types/product";
import { useAppSelector, useAppDispatch } from "../redux/hooks";
import { fetchProductById } from "../redux/features/product/productSlice";

export const useProductDetail = () => {
  const { id } = useParams();
  const dispatch = useAppDispatch();

  const variations = useAppSelector((state) => state.products.variations);

  const [selectedColor, setSelectedColor] = useState<string | undefined>("");
  const [selectedRam, setSelectedRam] = useState<string | undefined>("");
  const [selectedSsd, setSelectedSsd] = useState<string | undefined>("");
  const [selectedQuantity, setSelectedQuantity] = useState("1");

  const handleQuantityChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSelectedQuantity(e.target.value);
  };

  useEffect(() => {
    if (variations.length === 0) {
      dispatch(fetchProductById(Number(id)));
    }
  }, [dispatch, id, variations]);

  useEffect(() => {
    if (variations) {
      const color = variations.find(
        (variation: ProductVariation) => variation.name === "color"
      );
      const ram = variations.find(
        (variation: ProductVariation) => variation.name === "ram"
      );
      const ssd = variations.find(
        (variation: ProductVariation) => variation.name === "ssd"
      );

      setSelectedColor(color?.variationOptions[0].value);
      setSelectedRam(ram?.variationOptions[0].value);
      setSelectedSsd(ssd?.variationOptions[0].value);
    }
  }, [variations, setSelectedColor, setSelectedRam, setSelectedSsd]);

  return {
    selectedColor,
    selectedRam,
    selectedSsd,
    selectedQuantity,
    handleQuantityChange,
  };
};
