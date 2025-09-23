import { useProperty } from "@/context/PropertyContext";

const PropertyView = () => {
  const { data } = useProperty();

  return (
    <div>
      <h2>{data.title}</h2>
      <div>
        {data.images.map((image) => (
          <img src={URL.createObjectURL(image)} />
        ))}
      </div>

      <h3>{data.address}</h3>
      <div>
        <h4>
          {data.maxGuest} {data.maxGuest > 1 ? "guests" : "guest"}
        </h4>
        <h4>
          {data.totalBedroom} {data.totalBedroom > 1 ? "bedrooms" : "bedroom"}
        </h4>
        <h4>
          {data.totalBed} {data.totalBed > 1 ? "beds" : "bed"}
        </h4>
        <h4>
          {data.totalBath} {data.totalBath > 1 ? "bath" : "baths"}
        </h4>
      </div>

      <p>{data.description}</p>
    </div>
  );
};

export default PropertyView;
