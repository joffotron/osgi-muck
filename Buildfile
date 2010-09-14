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

class CentralLayout < Layout::Default
  def initialize(key, top_level, use_subdir)
    super()
    prefix = top_level ? '' : '../'
    subdir = use_subdir ? "/#{key}" : ''
    self[:target] = "#{prefix}target#{subdir}"
    self[:target, :main] = "#{prefix}target#{subdir}"
    self[:reports] = "#{prefix}reports#{subdir}"
  end
end

def define_with_central_layout(name, top_level = false, use_subdir = true, & block)
  define(name, :layout => CentralLayout.new(name, top_level, use_subdir), & block)
end


desc 'Mucking around in OSGi'
define_with_central_layout('osgimuck', true, false ) do
  project.version = '1.0'
  project.group = 'brutalbits'
  compile.options.source = '1.6'
  compile.options.target = '1.6'
  compile.options.lint = 'all'

  #ipr.template = _('etc/project-template.ipr')

  desc "Greeting data provider"
  define_with_central_layout 'greeter' do
    compile.with OSGI_CORE
    test.using :testng

    package(:bundle).tap do |bnd|
      bnd['Export-Package'] = "com.brutalbits.osgimuck.greeter.api.*;version=#{version}"
      bnd['Bundle-Activator'] = "com.brutalbits.osgimuck.greeter.GreetingActivator"
    end
  end

  desc "Greeter WebService"
  define_with_central_layout 'webservice'  do
    compile.with OSGI_CORE, projects('greeter')
    test.using :testng

    package(:bundle).tap do |bnd|
      bnd['Export-Package'] = "com.brutalbits.osgimuck.webservice.*;version=#{version}"
      bnd['Bundle-Activator'] = "com.brutalbits.osgimuck.webservice.GreetingService"
    end

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

