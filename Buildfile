gem 'buildr-bnd', :version => '0.0.5'
gem 'buildr-iidea', :version => '0.0.7'
#gem 'buildr-ipojo', :version => '0.0.1'

require 'buildr_bnd'
require 'buildr_iidea'
#require 'buildr_ipojo'

repositories.remote << 'https://repository.apache.org/content/repositories/releases'
repositories.remote << 'http://www.ibiblio.org/maven2'
repositories.remote << 'http://repository.springsource.com/maven/bundles/external'

repositories.remote << Buildr::Bnd.remote_repository
#repositories.remote << Buildr::Ipojo.remote_repository

#IPOJO_ANNOTATIONS = Buildr::Ipojo.annotation_artifact

OSGI_CORE = 'org.apache.felix:org.osgi.core:jar:1.4.0'
OSGI_COMPENDIUM = 'org.apache.felix:org.osgi.compendium:jar:1.4.0'

KARAF_DIR="C:/java/apache-karaf-2.0.1-SNAPSHOT/"

desc 'Mucking around in OSGi'
define 'osgimuck' do
  project.version = '1.0'
  project.group = 'brutalbits'
  compile.options.source = '1.6'
  compile.options.target = '1.6'
  compile.options.lint = 'all'

  ipr.template = _('etc/project-template.ipr')

  desc "Greeting data provider"
  define 'greeter' do
    compile.with OSGI_CORE
    test.using :testng

    package(:bundle).tap do |bnd|
      bnd['Private-Package'] = "com.brutalbits.osgimuck.greeter.*"
      bnd['Export-Package'] = "com.brutalbits.osgimuck.greeter.api.*;version=#{version}"
      bnd['Bundle-Activator'] = "com.brutalbits.osgimuck.greeter.WatchActivator"
    end
  end

  desc "Greeter WebService"
  define 'webservice' do
    compile.with OSGI_CORE, projects('greeter')
    test.using :testng

    package(:war).with(
      :libs => [],   #override so don't package the other project, or OSGI_CORE

      :manifest =>
      {
        'Private-Package' => 'com.brutalbits.osgimuck.webservice',
        #Buildr is spliting the import package into seperate lines, which breaks the deployment :(
        'Import-Package' => 'javax.jws;version="2.0",org.osgi.framework;version="1.5",com.brutalbits.osgimuck.greeter.api;version="1.0"',
        'Web-ContextPath' => "/MyServices",
        'Bundle-ClassPath' => 'WEB-INF/classes/',
        'Bundle-Name' => 'Web Service module of our application',
        'Bundle-ManifestVersion' => '2',
        'Bundle-SymbolicName' => 'com.brutalbits.osgimuck.webservice',
        'Bundle-Version' => '1.0.0',
        'Tool' => 'Bnd-0.0.311-not-really',
        'Bnd-LastModified' => '1284533928268'
      })

    #package(:bundle).tap do |bnd|
    #  bnd['Export-Package'] = "com.brutalbits.osgimuck.webservice.*;version=#{version}"
    #  bnd['Bundle-Activator'] = "com.brutalbits.osgimuck.webservice.GreeterServiceActivator"
    #end

  end

  desc "Deploy files require to run to a Karaf instance"
  task :deploy_to_karaf do
    cp artifacts([
      project('webservice').package(:bundle),
      project('greeter').package(:bundle)]).collect { |a| a.invoke; a.to_s },
       "#{KARAF_DIR}/deploy/"
    cp_r Dir["#{_('etc/dist')}/**"], KARAF_DIR
  end
end

