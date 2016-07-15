package service

import java.util
import javax.persistence.{Embeddable, Entity}

import jdk.nashorn.internal.ir.annotations.Ignore
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import org.hibernate.cfg.Configuration
import org.hibernate.{Query, Session, SessionFactory, Transaction}

/**
  * Created by vitaliys on 13.07.16.
  */

@Entity
class GlobalService{
  val configuration: Configuration = new Configuration().configure()
  val builder: StandardServiceRegistryBuilder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties)
  val factory: SessionFactory = configuration.buildSessionFactory(builder.build())
  val session: Session = factory.openSession()
  var tx: Transaction = null

  def zIssueTrackingIssueWorklogs(): util.List[_] = {
    try {
      tx = session.beginTransaction()
      val hql: String = "select UserKey from zIssueTrackingIssueWorklogs"
      val query: Query = session.createQuery(hql)
      val result: util.List[_] = query.list()
      tx.commit()
      result
    } finally {
      session.close()
    }
  }

}



