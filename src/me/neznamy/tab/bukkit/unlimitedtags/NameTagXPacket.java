package me.neznamy.tab.bukkit.unlimitedtags;

import java.lang.reflect.Field;

import me.neznamy.tab.bukkit.packets.NMSClass;
import me.neznamy.tab.shared.ProtocolVersion;
import me.neznamy.tab.shared.Shared;

public class NameTagXPacket {

	private PacketType type;
	private int entityId;
	private int[] entityArray;
	private int extra;
	
	public NameTagXPacket(PacketType type, int entityId, int[] entityArray, int extra) {
		this.type = type;
		this.entityId = entityId;
		this.entityArray = entityArray;
		this.extra = extra;
	}
	public PacketType getPacketType() {
		return type;
	}
	public int getEntityId() {
		return entityId;
	}
	public int[] getEntityArray() {
		return entityArray;
	}
	public int getExtra() {
		return extra;
	}
	public static NameTagXPacket fromNMS(Object nmsPacket) throws Exception {
		if (PacketPlayOutNamedEntitySpawn.isInstance(nmsPacket)) return new NameTagXPacket(PacketType.NAMED_ENTITY_SPAWN, PacketPlayOutNamedEntitySpawn_ENTITYID.getInt(nmsPacket), null, -1);
		if (PacketPlayOutEntityDestroy.isInstance(nmsPacket)) return new NameTagXPacket(PacketType.ENTITY_DESTROY, -1, (int[]) PacketPlayOutEntityDestroy_ENTITIES.get(nmsPacket), -1);
		if (PacketPlayOutEntityTeleport.isInstance(nmsPacket)) return new NameTagXPacket(PacketType.ENTITY_TELEPORT, PacketPlayOutEntityTeleport_ENTITYID.getInt(nmsPacket), null, -1);
		if (PacketPlayOutRelEntityMove.isInstance(nmsPacket)) return new NameTagXPacket(PacketType.ENTITY_MOVE, PacketPlayOutEntity_ENTITYID.getInt(nmsPacket), null, -1);
		if (PacketPlayOutRelEntityMoveLook.isInstance(nmsPacket)) return new NameTagXPacket(PacketType.ENTITY_MOVE, PacketPlayOutEntity_ENTITYID.getInt(nmsPacket), null, -1);
		if (PacketPlayOutMount != null && PacketPlayOutMount.isInstance(nmsPacket)) return new NameTagXPacket(PacketType.MOUNT, PacketPlayOutMount_VEHICLE.getInt(nmsPacket), (int[]) PacketPlayOutMount_PASSENGERS.get(nmsPacket), -1);
		if (PacketPlayOutAttachEntity != null && PacketPlayOutAttachEntity.isInstance(nmsPacket)) return new NameTagXPacket(PacketType.ATTACH_ENTITY, PacketPlayOutAttachEntity_VEHICLE.getInt(nmsPacket), new int[] {PacketPlayOutAttachEntity_PASSENGER.getInt(nmsPacket)}, PacketPlayOutAttachEntity_A.getInt(nmsPacket));
		return null;
	}
	
	public static enum PacketType{
		NAMED_ENTITY_SPAWN, //spawning armor stand
		ENTITY_DESTROY, //destroying armor stand
		ENTITY_TELEPORT, //teleporting armor stand
		ENTITY_MOVE, //teleporting armor stand
		MOUNT, //1.9+ mount detection
		ATTACH_ENTITY; //1.8.x mount detection
	}
	
	private static Class<?> PacketPlayOutNamedEntitySpawn;
	private static Field PacketPlayOutNamedEntitySpawn_ENTITYID;
	
	private static Class<?> PacketPlayOutEntityDestroy;
	private static Field PacketPlayOutEntityDestroy_ENTITIES;
	
	private static Class<?> PacketPlayOutEntityTeleport;
	private static Field PacketPlayOutEntityTeleport_ENTITYID;
	
	private static Class<?> PacketPlayOutRelEntityMove;
	private static Class<?> PacketPlayOutRelEntityMoveLook;
	private static Field PacketPlayOutEntity_ENTITYID;
	
	private static Class<?> PacketPlayOutMount;
	private static Field PacketPlayOutMount_VEHICLE;
	private static Field PacketPlayOutMount_PASSENGERS;
	
	private static Class<?> PacketPlayOutAttachEntity;
	private static Field PacketPlayOutAttachEntity_A;
	private static Field PacketPlayOutAttachEntity_PASSENGER;
	private static Field PacketPlayOutAttachEntity_VEHICLE;
	
	static {
		try {
			if (ProtocolVersion.SERVER_VERSION.getMinorVersion() == 8) {
				PacketPlayOutAttachEntity = NMSClass.getClass("PacketPlayOutAttachEntity");
				(PacketPlayOutAttachEntity_A = PacketPlayOutAttachEntity.getDeclaredField("a")).setAccessible(true);
				(PacketPlayOutAttachEntity_PASSENGER = PacketPlayOutAttachEntity.getDeclaredField("b")).setAccessible(true);
				(PacketPlayOutAttachEntity_VEHICLE = PacketPlayOutAttachEntity.getDeclaredField("c")).setAccessible(true);
			}
			if (ProtocolVersion.SERVER_VERSION.getMinorVersion() >= 8) {
				(PacketPlayOutNamedEntitySpawn_ENTITYID = (PacketPlayOutNamedEntitySpawn = NMSClass.getClass("PacketPlayOutNamedEntitySpawn")).getDeclaredField("a")).setAccessible(true);
				(PacketPlayOutEntityDestroy_ENTITIES = (PacketPlayOutEntityDestroy = NMSClass.getClass("PacketPlayOutEntityDestroy")).getDeclaredField("a")).setAccessible(true);
				(PacketPlayOutEntityTeleport_ENTITYID = (NMSClass.getClass("PacketPlayOutEntityTeleport")).getDeclaredField("a")).setAccessible(true);
				(PacketPlayOutEntity_ENTITYID = (NMSClass.getClass("PacketPlayOutEntity")).getDeclaredField("a")).setAccessible(true);
				if (ProtocolVersion.packageName.equals("v1_8_R1")) {
					PacketPlayOutRelEntityMove = NMSClass.getClass("PacketPlayOutRelEntityMove");
					PacketPlayOutRelEntityMoveLook = NMSClass.getClass("PacketPlayOutRelEntityMoveLook");
				} else {
					PacketPlayOutRelEntityMove = NMSClass.getClass("PacketPlayOutEntity$PacketPlayOutRelEntityMove");
					PacketPlayOutRelEntityMoveLook = NMSClass.getClass("PacketPlayOutEntity$PacketPlayOutRelEntityMoveLook");
				}
				(PacketPlayOutEntityTeleport_ENTITYID = (PacketPlayOutEntityTeleport = NMSClass.getClass("PacketPlayOutEntityTeleport")).getDeclaredField("a")).setAccessible(true);
			}
			if (ProtocolVersion.SERVER_VERSION.getMinorVersion() >= 9) {
				PacketPlayOutMount = NMSClass.getClass("PacketPlayOutMount");
				(PacketPlayOutMount_VEHICLE = PacketPlayOutMount.getDeclaredField("a")).setAccessible(true);
				(PacketPlayOutMount_PASSENGERS = PacketPlayOutMount.getDeclaredField("b")).setAccessible(true);
			}
		} catch (Throwable e) {
			Shared.error("Failed to initialize NameTagXPacket class", e);
		}
	}
}