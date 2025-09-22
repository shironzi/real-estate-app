import {
  Dispatch,
  ReactNode,
  SetStateAction,
  useState,
  createContext,
  useContext,
} from "react";

type PropertyData = {
  title: string;
  price: number;
  address: string;
  description: string;
  maxGuest: number;
  totalBedroom: number;
  city: string;
  propertyType: "APARTMENT" | "HOUSE" | "VILLA" | "CABIN";
  images: File[];
};

type PropertyContextType = {
  data: PropertyData;
  setData: Dispatch<SetStateAction<PropertyData>>;
};

const propertyData: PropertyData = {
  title: "",
  price: 0,
  address: "",
  description: "",
  maxGuest: 1,
  totalBedroom: 0,
  city: "",
  propertyType: "APARTMENT",
  images: [],
};

export const PropertyContext = createContext<PropertyContextType>({
  data: propertyData,
  setData: () => {},
});

export const PropertyProvider = ({ children }: { children: ReactNode }) => {
  const [data, setData] = useState<PropertyData>(propertyData);

  return (
    <PropertyContext.Provider value={{ data, setData }}>
      {children}
    </PropertyContext.Provider>
  );
};

export const useProperty = () => {
  const ctx = useContext(PropertyContext);
  return ctx;
};
