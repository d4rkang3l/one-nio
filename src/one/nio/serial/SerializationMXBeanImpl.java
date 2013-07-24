package one.nio.serial;

import one.nio.util.Hex;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class SerializationMXBeanImpl implements SerializationMXBean {

    SerializationMXBeanImpl() {
        // Created only once
    }

    @Override
    public List<String> getClassSerializers() {
        return getSerializerList(Repository.classMap);
    }

    @Override
    public List<String> getUidSerializers() {
        return getSerializerList(Repository.uidMap);
    }

    @Override
    public String getSerializer(String uid) {
        try {
            return Repository.requestSerializer(Hex.parseLong(uid)).toString();
        } catch (SerializerNotFoundException e) {
            return null;
        }
    }

    @Override
    public int getSerializersSent() {
        return Serializer.serializersSent.get();
    }

    @Override
    public int getSerializersReceived() {
        return Serializer.serializersReceived.get();
    }

    @Override
    public int getAnonymousClasses() {
        return Repository.anonymousClasseses;
    }

    @Override
    public int getRenamedClasses() {
        return Repository.renamedClasses.size();
    }

    @Override
    public int getNewTypes() {
        return GeneratedSerializer.newTypes.get();
    }

    @Override
    public int getMissedLocalFields() {
        return GeneratedSerializer.missedLocalFields.get();
    }

    @Override
    public int getMissedStreamFields() {
        return GeneratedSerializer.missedStreamFields.get();
    }

    @Override
    public int getMigratedFields() {
        return GeneratedSerializer.migratedFields.get();
    }

    @Override
    public int getRenamedFields() {
        return GeneratedSerializer.renamedFields.get();
    }

    @Override
    public int getEnumOldSerializers() {
        return EnumSerializer.enumOldSerializers.get();
    }

    @Override
    public int getEnumCountMismatches() {
        return EnumSerializer.enumCountMismatches.get();
    }

    @Override
    public int getEnumMissedConstants() {
        return EnumSerializer.enumMissedConstants.get();
    }

    private List<String> getSerializerList(Map<?, Serializer> map) {
        synchronized (Repository.class) {
            ArrayList<String> result = new ArrayList<String>(map.size());
            for (Serializer serializer : map.values()) {
                result.add(serializer.uid());
            }
            return result;
        }
    }
}
