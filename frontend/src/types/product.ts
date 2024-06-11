export type ProductVariationOption = {
  id: number;
  value: string;
};

export type ProductVariation = {
  id: number;
  name: string;
  variationOptions: ProductVariationOption[];
};

export type Product = {
  id: number;
  name: string;
  category: string;
  description: string;
  imageURLs: [
    {
      id: number;
      imagePath: string;
      color: string;
    }
  ];
  variations: ProductVariation[];
};
