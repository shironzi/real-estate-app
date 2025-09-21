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
};

type PropertyContextType = {
  data: PropertyData;
  setData: Dispatch<SetStateAction<PropertyData>>;
};

export const PropertyContext = createContext<PropertyContextType>({
  data: {
    title: "",
    price: 0,
    address: "",
    description: "",
    maxGuest: 1,
    totalBedroom: 0,
    city: "",
    propertyType: "APARTMENT",
  },
  setData: () => {},
});

export const PropertyProvider = ({ children }: { children: ReactNode }) => {
  const [data, setData] = useState<PropertyData>({
    title: "",
    price: 0,
    address: "",
    description: "",
    maxGuest: 1,
    totalBedroom: 0,
    city: "",
    propertyType: "APARTMENT",
  });

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
