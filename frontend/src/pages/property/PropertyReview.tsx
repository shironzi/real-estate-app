import { useProperty } from "@/context/PropertyContext";
import { createProperty } from "@/utils/property";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

const PropertyReview = () => {
  const navigate = useNavigate();

  const { data } = useProperty();
  const [message, setMessage] = useState<string>();
  const [isFetching, setIsFetching] = useState<boolean>(false);

  const handleBack = () => {
    navigate("/property/images");
  };

  const handleCreate = async () => {
    setIsFetching(true);
    try {
      const res = await createProperty(data);

      setMessage(res?.message || "Successfully created a property.");
      navigate("/property/manage");
    } catch (e: any) {
      setMessage(
        e?.response?.data?.message || e.message || "Something went wrong"
      );

      console.error(e);
    } finally {
      setIsFetching(false);
    }
  };

  return (
    <div>
      <h2>{data.title}</h2>
      <div>
        {data.images.map((image, index) => (
          <img
            src={URL.createObjectURL(image)}
            key={index}
            alt="property_image_preview"
          />
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

      <div className="property-button-container">
        <button
          onClick={handleBack}
          className="back-button"
          disabled={isFetching}
        >
          <h3>Prev</h3>
        </button>
        <button onClick={handleCreate} disabled={isFetching}>
          <h3>{isFetching ? "Creating...." : "Create"}</h3>
        </button>
      </div>
    </div>
  );
};

export default PropertyReview;
