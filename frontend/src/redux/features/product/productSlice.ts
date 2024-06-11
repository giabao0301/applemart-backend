import { createSlice, createAsyncThunk, PayloadAction } from "@reduxjs/toolkit";
import { getProduct, getProductById } from "../../../api/lib/product";
import { Product, ProductVariation } from "../../../types/product";
import { RootState } from "../../store";

interface ProductState {
  products: Product[];
  product: Product | null;
  variations: ProductVariation[];
  status: "idle" | "loading" | "succeeded" | "failed";
  error: string | null;
}

const initialState: ProductState = {
  products: [],
  product: null,
  variations: [],
  status: "idle",
  error: null,
};

export const fetchProducts = createAsyncThunk(
  "products/fetchProducts",
  async () => {
    const response = await getProduct();
    return response.data;
  }
);

export const fetchProductById = createAsyncThunk(
  "products/fetchProduct",
  async (id: number) => {
    const response = await getProductById(id);
    return response.data;
  }
);

export const productSlice = createSlice({
  name: "products",
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchProducts.pending, (state) => {
        state.status = "loading";
      })
      .addCase(
        fetchProducts.fulfilled,
        (state, action: PayloadAction<Array<Product>>) => {
          state.status = "succeeded";
          state.products = action.payload;
        }
      )
      .addCase(fetchProducts.rejected, (state) => {
        state.status = "failed";
        state.error = "Failed to fetch products";
      })
      .addCase(fetchProductById.pending, (state) => {
        state.status = "loading";
      })
      .addCase(
        fetchProductById.fulfilled,
        (state, action: PayloadAction<Product>) => {
          state.status = "succeeded";
          state.product = action.payload;
          state.variations = action.payload.variations;
        }
      )
      .addCase(fetchProductById.rejected, (state) => {
        state.status = "failed";
        state.error = "Failed to fetch product";
      });
  },
});

// export const { } = productSlice.actions

export const selectProducts = (state: RootState) => state.products.products;

export default productSlice.reducer;
