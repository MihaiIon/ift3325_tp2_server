package models;

import managers.CheckSumManager;
import managers.ConversionManager;
import managers.DataManager;

import static models.PacketModel.Type.INFORMATION;

public class PacketModel {

    // ------------------------------------------------------------------------
    // Static

    /**
     * Used in the constructor to specifiy the type (role)
     * of the packet that is sent.
     */
    public enum Type {
        INFORMATION,
        CONNECTION_REQUEST,
        PACKET_RECEPTION,
        REJECTED_PACKET,
        ENDING_CONNECTION,
        P_BITS
    }

    /**
     * Converts the provided binary data to a PacketModel Object.
     * @param stream Stream of bits representing the packet.
     * @return PacketModel Object.
     */
    public static PacketModel convertToPacket(String stream) {
        // Save lengths
        int streamLength = stream.length();
        int generatorLength = CheckSumManager.generator.length();
        // Parse type
        Type type = parseType(stream.substring(0, 8));
        // Parse Frame
        switch (type) {
            case INFORMATION:
                byte id = ConversionManager.convertStringToByte(stream.substring(8, 16));
                String data = stream.substring(16, streamLength - generatorLength);
                String checkSum = stream.substring(streamLength - generatorLength);
                return new PacketModel(id, type, new PayloadModel(data, checkSum));
            case CONNECTION_REQUEST:
                return new PacketModel((byte)0, type, new PayloadModel("", ""));
            case PACKET_RECEPTION:
                return new PacketModel((byte)0, type, new PayloadModel("", ""));
            case REJECTED_PACKET:
                return new PacketModel((byte)0, type, new PayloadModel("", ""));
            case ENDING_CONNECTION:
                return new PacketModel((byte)0, type, new PayloadModel("", ""));
            default:
                return new PacketModel((byte)0, type, new PayloadModel("", ""));
        }
    }

    /**
     * @param type The type of the packet.
     * @return Encodes the type of the packet on 8 bits (byte).
     */
    private static byte convertTypeToByte(Type type) {
        switch (type) {
            case INFORMATION:
                return (byte) 'I';
            case CONNECTION_REQUEST:
                return (byte) 'C';
            case PACKET_RECEPTION:
                return (byte) 'A';
            case REJECTED_PACKET:
                return (byte) 'R';
            case ENDING_CONNECTION:
                return (byte) 'F';
            default:
                return (byte) 'P';
        }
    }

    /**
     *
     * @param byteStream
     * @return
     */
    private static Type parseType (String byteStream) {
        return Type.INFORMATION;
    }

    // ------------------------------------------------------------------------
    // Packet Model

    // Attributes
    private byte id;
    private byte type;
    private PayloadModel payload;

    /**
     * Information frame constructor.
     * @param id Identifies the packet (0-7).
     * @param type Identifies the type of the packet (see class Type).
     * @param payload Frame's data.
     */
    public PacketModel(byte id, Type type, PayloadModel payload) {
        this.id = id;
        this.type = PacketModel.convertTypeToByte(type);
        this.payload = payload;
    }

    /**
     * Converts PacketModel object to binary number (String representation).
     */
    public String toBinary() {
        StringBuilder sb = new StringBuilder();
        sb.append(ConversionManager.convertByteToString(DataManager.FLAG));
        sb.append(ConversionManager.convertByteToString(type));
        switch (getType()) {
            case INFORMATION:
                sb.append(ConversionManager.convertByteToString(id));
                sb.append(payload.toString());
                break;
            case PACKET_RECEPTION:
                break;
            case REJECTED_PACKET:
                break;
            case ENDING_CONNECTION:
                break;
            default:

        }
        sb.append(ConversionManager.convertByteToString(DataManager.FLAG));
        return sb.toString();
    }

    // ------------------------------------------------------------------------
    // Getters

    /**
     * @return Provides the identifier of the packet.
     */
    public int getId() {
        return this.id;
    }

    /**
     * @return Provides the type of the packet.
     */
    public Type getType() {
        char type = (char) (this.type & 0xFF);
        switch (type) {
            case 'I':
                return INFORMATION;
            case 'C':
                return Type.CONNECTION_REQUEST;
            case 'A':
                return Type.PACKET_RECEPTION;
            case 'R':
                return Type.REJECTED_PACKET;
            case 'F':
                return Type.ENDING_CONNECTION;
            default:
                return Type.P_BITS;
        }
    }

    public PayloadModel getPayload() { return payload; }
    public String getData() { return payload.getData(); }
    public String getCheckSum() {
        return payload.getCheckSum();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Packet : ");
        sb.append("\n\ttype : ").append(getType());
        switch (getType()) {
            case INFORMATION:
                sb.append("\n\tid : ").append(getId());
                sb.append("\n\tdata : ").append(ConversionManager.convertStreamToReadableStream(getData()));
                sb.append("\n\tcheckSum : ").append(ConversionManager.convertStreamToReadableStream(getCheckSum()));
                break;
            case PACKET_RECEPTION:
                break;
            case REJECTED_PACKET:
                break;
            case ENDING_CONNECTION:
                break;
            default:
        }
        sb.append("\n\tbinary : " + toBinary());
        return sb.toString();
    }
}