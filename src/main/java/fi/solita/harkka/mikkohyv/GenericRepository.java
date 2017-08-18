package fi.solita.harkka.mikkohyv;


import java.util.ArrayList;
import java.util.List;

public class GenericRepository<Entity> implements IGenericRepository<Entity> {

    public List<Entity> repositoryData;

    public GenericRepository(){
        repositoryData = new ArrayList<Entity>();
    }


}
