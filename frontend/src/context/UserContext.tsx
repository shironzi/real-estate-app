import {
  createContext,
  Dispatch,
  ReactNode,
  SetStateAction,
  useContext,
  useState,
} from "react";

type UserContextTypes = {
  name: string;
  email: string;
  role: string;
};

const defaultData: UserContextTypes = {
  name: "",
  email: "",
  role: "RENTER",
};

type userData = {
  data: UserContextTypes;
  setData: Dispatch<SetStateAction<UserContextTypes>>;
};

export const UserContext = createContext<userData>({
  data: defaultData,
  setData: () => {},
});

export const PropertyProvider = ({ children }: { children: ReactNode }) => {
  const [data, setData] = useState<UserContextTypes>(defaultData);

  return (
    <UserContext.Provider value={{ data, setData }}>
      {children}
    </UserContext.Provider>
  );
};

export const useProperty = () => {
  const ctx = useContext(UserContext);
  return ctx;
};
