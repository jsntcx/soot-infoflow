package soot.jimple.infoflow;

import java.util.Collections;
import java.util.List;

import soot.Transform;
import soot.jimple.infoflow.entryPointCreators.DefaultEntryPointCreator;
import soot.jimple.infoflow.entryPointCreators.IEntryPointCreator;
import soot.jimple.infoflow.source.DefaultSourceSinkManager;
import soot.jimple.infoflow.taintWrappers.ITaintPropagationWrapper;

/**
 * Abstract base class for all data/information flow analyses in FlowDroid
 * @author sarzt
 *
 */
public abstract class AbstractInfoflow implements IInfoflow {

	protected ITaintPropagationWrapper taintWrapper;

	protected boolean stopAfterFirstFlow = false;
	protected boolean enableImplicitFlows = false;
	protected boolean enableStaticFields = true;
	protected boolean enableExceptions = true;
	protected boolean computeResultPaths = true;
	protected boolean flowSensitiveAliasing = true;

	protected boolean inspectSources = false;
	protected boolean inspectSinks = false;
	
	protected final BiDirICFGFactory icfgFactory;
	protected int maxThreadNum = -1;

	protected CallgraphAlgorithm callgraphAlgorithm = CallgraphAlgorithm.AutomaticSelection;
	protected AliasingAlgorithm aliasingAlgorithm = AliasingAlgorithm.FlowSensitive;
	
	protected List<Transform> preProcessors = Collections.emptyList();
    
    /**
     * Creates a new instance of the abstract info flow problem
     */
    public AbstractInfoflow() {
    	this.icfgFactory = new DefaultBiDiICFGFactory();
    }

    /**
     * Creates a new instance of the abstract info flow problem
     * @param icfgFactory The interprocedural CFG to be used by the InfoFlowProblem
     */
    public AbstractInfoflow(BiDirICFGFactory icfgFactory) {
    	this.icfgFactory = icfgFactory;
    }

    @Override
	public void setTaintWrapper(ITaintPropagationWrapper wrapper) {
		taintWrapper = wrapper;
	}

	@Override
	public void setStopAfterFirstFlow(boolean stopAfterFirstFlow) {
		this.stopAfterFirstFlow = stopAfterFirstFlow;
	}
	
	@Override
	public void setPreProcessors(List<Transform> preprocessors) {
        this.preProcessors = preprocessors;
	}

	@Override
	public void computeInfoflow(String path,
			IEntryPointCreator entryPointCreator, List<String> entryPoints,
			List<String> sources, List<String> sinks) {
		this.computeInfoflow(path, entryPointCreator, entryPoints,
				new DefaultSourceSinkManager(sources, sinks));
	}

	@Override
	public void computeInfoflow(String path, List<String> entryPoints,
			List<String> sources, List<String> sinks) {
		this.computeInfoflow(path, new DefaultEntryPointCreator(), entryPoints,
				new DefaultSourceSinkManager(sources, sinks));
	}

	@Override
	public void computeInfoflow(String path, String entryPoint,
			List<String> sources, List<String> sinks) {
		this.computeInfoflow(path, entryPoint, new DefaultSourceSinkManager(sources, sinks));
	}
	
	@Override
	public void setInspectSources(boolean inspect) {
		inspectSources = inspect;
	}

	@Override
	public void setInspectSinks(boolean inspect) {
		inspectSinks = inspect;
	}

	@Override
	public void setEnableImplicitFlows(boolean enableImplicitFlows) {
		this.enableImplicitFlows = enableImplicitFlows;
	}

	@Override
	public void setEnableStaticFieldTracking(boolean enableStaticFields) {
		this.enableStaticFields = enableStaticFields;
	}

	@Override
	public void setComputeResultPaths(boolean computeResultPaths) {
		this.computeResultPaths = computeResultPaths;
	}

	@Override
	public void setFlowSensitiveAliasing(boolean flowSensitiveAliasing) {
		this.flowSensitiveAliasing = flowSensitiveAliasing;
	}

	@Override
	public void setEnableExceptionTracking(boolean enableExceptions) {
		this.enableExceptions = enableExceptions;
	}

	@Override
	public void setCallgraphAlgorithm(CallgraphAlgorithm algorithm) {
    	this.callgraphAlgorithm = algorithm;
	}

	@Override
	public void setAliasingAlgorithm(AliasingAlgorithm algorithm) {
    	this.aliasingAlgorithm = algorithm;
	}
	
	@Override
	public void setMaxThreadNum(int threadNum) {
		this.maxThreadNum = threadNum;
	}

}
